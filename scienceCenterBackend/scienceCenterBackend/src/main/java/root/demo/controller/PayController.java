package root.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import root.demo.model.MagazineDto;
import root.demo.model.MagazineDto2;
import root.demo.model.NewClientResponse;
import root.demo.model.NewMagazineConfirmationDto;
import root.demo.model.OrderIdDTO;
import root.demo.model.ShoppingCartDto;
import root.demo.model.ShoppingCartRequestKpDto;
import root.demo.model.TxInfoDto;
import root.demo.model.repo.Magazine;
import root.demo.model.user.tx.UserTx;
import root.demo.repository.MagazineRepository;
import root.demo.repository.UserTxRepository;



@RestController
@RequestMapping("/pay")
@CrossOrigin
public class PayController {
	
	@Autowired
	private MagazineRepository magazineRepository;
	
	@Autowired
	private UserTxRepository userTxRepository;
	
	@Value("${server.port}")
	private String webShopClientport;
	
	
	@RequestMapping(path = "/cart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cartPost(@RequestBody ShoppingCartDto request) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		//izvuci tu tx iz baze 
		UserTx cart = userTxRepository.getOne(request.getCartId());
		
		//no items
		if(cart.getItems().size() == 0) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		//dobavi nekako sellerId -> sadrzaj korpe od isto prodavca/tj. casopisa!!! SETOVANO PRI DODAVANJU U KORPU
//		cart.setkPIdentifier(1l);

		
		//String url = cart.getUrl();
//		String url = "https://localhost:8111/request/save";
		String url = "https://localhost:8762/requestHandler/request/save";

		
		ShoppingCartRequestKpDto kpRequest = new ShoppingCartRequestKpDto(cart.getkPIdentifier(), cart.getTotalAmount());
		
		//ResponseEntity<OrderIdDTO> dto = restTemplate.postForEntity(url, kpRequest, OrderIdDTO.class);
		
		ResponseEntity<OrderIdDTO> dto = null;
		try {
			dto = restTemplate.exchange(url, HttpMethod.POST, createHeader(kpRequest), OrderIdDTO.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		// za izvucenu tu tx iz baze dodeliti id iz kpA
		cart.setOrderId(dto.getBody().getOrderId());
		userTxRepository.save(cart);
		
		return new ResponseEntity<OrderIdDTO>(dto.getBody(), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/updateTxAfterPaymentIsFinished", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TxInfoDto> updateTxInTheEnd(@RequestBody TxInfoDto request) {		
		
//		UserTx userTx = userTxRepository.findBykPClientIdentifier(request.getOrderId());
		UserTx userTx = userTxRepository.findByOrderId(request.getOrderId());

		userTx.setStatus(request.getStatus());
		
		userTxRepository.save(userTx);
		
		return new ResponseEntity<TxInfoDto>(request, HttpStatus.OK);
	}
	
	@PostMapping(path = "/register")
	public ResponseEntity<?> newMagazine(@RequestBody MagazineDto2 magazineDto){
		Magazine newMagazine = new Magazine(magazineDto.getISSN(), magazineDto.getName(), magazineDto.getWayOfPayment(), true, -1l, magazineDto.getPrice());
		Magazine persistedMagazine = magazineRepository.save(newMagazine);
		
		String newClientRequestUrl = "https://localhost:8762/requestHandler/client/newClient/";
		
		RestTemplate restTemplate = new RestTemplate();
		// ResponseEntity<String> response = restTemplate.getForEntity(newClientRequestUrl + persistedMagazine.getMagazineId(), String.class);
		
		
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(newClientRequestUrl + persistedMagazine.getMagazineId(), HttpMethod.GET, createHeader(null), String.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		
		return new ResponseEntity<>(new NewClientResponse(response.getBody()), HttpStatus.OK);
	}
	
	@PostMapping(path = "/registerConfirmationBasic")
	public ResponseEntity<?> newMagazineConfirmationBasic(@RequestBody NewMagazineConfirmationDto request){
		
		Magazine magazine = magazineRepository.getOne(request.getScMagazineIdentifier());
		magazine.setSellerIdentifier(request.getKpMagazineIdentifier());
		magazineRepository.save(magazine);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private HttpEntity<?> createHeader (Object body){
		HttpHeaders headers = new HttpHeaders();
		headers.add("external", "true");
		headers.add("hostsc", "localhost:" + webShopClientport);
		
		HttpEntity<?> entity;
		if(body != null) {
			entity = new HttpEntity<>(body, headers);
		} else {
			entity = new HttpEntity<>(headers);
		}
		
		return entity;
	}

}
