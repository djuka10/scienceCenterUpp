package root.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import root.demo.model.ArticleDto;
import root.demo.model.MagazineDto2;
import root.demo.model.MagazineEditionArticleDto;
import root.demo.model.MagazineEditionDto;
import root.demo.model.NewCartItemRequest;
import root.demo.model.ShoppingCartDto;
import root.demo.model.UserTxDto;
import root.demo.model.UserTxItemDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.BuyingType;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MagazineEdition;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.TxStatus;
import root.demo.model.repo.UserA;
import root.demo.model.user.tx.UserTx;
import root.demo.model.user.tx.UserTxItem;
import root.demo.repository.ArticleRepository;
import root.demo.repository.MagazineEditionRepository;
import root.demo.repository.MagazineRepository;
import root.demo.repository.UserRepository;
import root.demo.repository.UserRepository2;
import root.demo.repository.UserTxItemRepository;
import root.demo.repository.UserTxRepository;
import root.demo.services.TestService;
import root.demo.util.Base64Utility;



@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private MagazineRepository magRepo;
	
	@Autowired
	private MagazineEditionRepository magEditionRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRepository2 userRepo2;
	
	@Autowired
	private UserTxRepository userTxRepository;
	
	@Autowired
	private UserTxItemRepository userTxItemRepository;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> test() {
		System.out.println("Uso");
		System.out.println(testService.callPaymentHub());
		return new ResponseEntity<>(new String("Okej NC radi poziv KP kako treba"), HttpStatus.OK);
	}
	
	@RequestMapping(path="/service", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> testService() {
		System.out.println(testService.callPaymentHubServices());
		return new ResponseEntity<>(new String("Okej NC radi poziv KP kako treba"), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/plain", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> testNC() {
		return new ResponseEntity<>(new String("Okej NC radi"), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MagazineDto2>> getAllNC() {
		
		List<Magazine> lista = magRepo.findAll();
		
		List<MagazineDto2> listaDTO = new ArrayList<MagazineDto2>();
		
		for (Magazine magazine : lista) {
			
			listaDTO.add(new MagazineDto2(magazine));
			
		}
		
		
		return new ResponseEntity<List<MagazineDto2>>(listaDTO, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/getOne/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MagazineDto2> getById(@PathVariable Long id) {
		
		Magazine mag = magRepo.getOne(id);
		
		
		return new ResponseEntity<MagazineDto2>(new MagazineDto2(mag), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/getOneEdition/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MagazineEditionDto> getEditionById(@PathVariable Long id) {
		
		MagazineEdition edition = magEditionRepo.getOne(id);
		
		
		return new ResponseEntity<MagazineEditionDto>(new MagazineEditionDto(edition.getMagazineEditionId(), edition.getPublishingDate(), edition.getMagazineEditionPrice()), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/getEditions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MagazineEditionDto>> getEditions(@PathVariable Long id) {
		
		Magazine mag = magRepo.getOne(id);
		Set<MagazineEdition> editions = mag.getMagazineEditions();
		List<MagazineEditionDto> editionsDto = new ArrayList<MagazineEditionDto>();
		
		for(MagazineEdition mE: editions) {
			editionsDto.add(new MagazineEditionDto(mE.getMagazineEditionId(), mE.getPublishingDate(), mE.getMagazineEditionPrice()));
		}
		
		return new ResponseEntity<List<MagazineEditionDto>>(editionsDto, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/getArticles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MagazineEditionArticleDto>> getArticles(@PathVariable Long id) {
		
		Magazine mag = magRepo.getOne(id);
		MagazineEdition edition = magEditionRepo.getOne(id);
		Set<Article> articles = edition.getArticles();
		List<MagazineEditionArticleDto> articlesDto = new ArrayList<MagazineEditionArticleDto>();
		
		for(Article a : articles) {
			articlesDto.add(new MagazineEditionArticleDto(a.getArticleId(), a.getArticleTitle(), a.getArticleAbstract(), a.getPublishingDate(), a.getDoi(), a.getArticlePrice(), ""));
		}
		
		return new ResponseEntity<List<MagazineEditionArticleDto>>(articlesDto, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/addItemToCart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MagazineEditionDto> addItemToCart(@RequestBody NewCartItemRequest request) {
		
		UserTx cartOrTx= userTxRepository.getOne(request.getCartId());
		
		//if first item
		if(cartOrTx.getkPIdentifier() == null) {
			BuyingType type = request.getBuyingType();
			MagazineEdition edition = null;
			Magazine magazine = null;
			switch (type) {
				case ARTICLE:
					Article article = articleRepo.getOne(request.getArticleId());
					edition = article.getMagazineEdition();
					magazine = edition.getMagazine();
					break;
				
				case MAGAZINE_EDITION:
					edition = magEditionRepo.getOne(request.getArticleId());
					magazine = edition.getMagazine();
					break;

			default:
				break;
			}
			
			cartOrTx.setkPIdentifier(magazine.getSellerIdentifier());
		} else {
			BuyingType type = request.getBuyingType();
			MagazineEdition edition = null;
			Magazine magazine = null;
			switch (type) {
				case ARTICLE:
					Article article = articleRepo.getOne(request.getArticleId());
					edition = article.getMagazineEdition();
					magazine = edition.getMagazine();
					break;
				
				case MAGAZINE_EDITION:
					edition = magEditionRepo.getOne(request.getArticleId());
					magazine = edition.getMagazine();
					break;

			default:
				break;
			}
			
			//different kp client constraint
			if(!(magazine.getSellerIdentifier().equals(cartOrTx.getkPIdentifier()))) {
				System.out.println("razlicito");
				return new ResponseEntity<>(new MagazineEditionDto(), HttpStatus.CONFLICT);
			}
		}
		
		BuyingType type = request.getBuyingType();
		long itemId = -1l;
		float price = -1f;
		switch (type) {
			case ARTICLE:
				Article article = articleRepo.getOne(request.getArticleId());
				price = article.getArticlePrice();
				itemId = article.getArticleId();
				break;
			
			case MAGAZINE_EDITION:
				
				break;

		default:
			break;
		}
		
		cartOrTx.setTotalAmount(cartOrTx.getTotalAmount() + price);
		UserTx cartOrTxUpdated = userTxRepository.save(cartOrTx);
		
		UserTxItem newUserTxItem = new UserTxItem(price, cartOrTxUpdated, type, itemId);
		
		userTxItemRepository.save(newUserTxItem);
		
		
		return new ResponseEntity<MagazineEditionDto>(new MagazineEditionDto(), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/removeItemFromCart/{itemId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MagazineEditionDto> removeItemFromCart(@PathVariable long itemId) {
		
		UserTxItem itemToDelete = userTxItemRepository.getOne(itemId);
		
		UserTx tx = itemToDelete.getUserTx();
		tx.setTotalAmount(tx.getTotalAmount() - itemToDelete.getPrice());
		userTxRepository.save(tx);
		
		userTxItemRepository.deleteById(itemId);
		
		return new ResponseEntity<MagazineEditionDto>(new MagazineEditionDto(), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/newCart/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ShoppingCartDto> newCart(@PathVariable Long userId) {
		
		//privremeno 
		MyUser myUser = userRepository.getOne(userId);
	//	UserA user = userRepo2.getOne(1l);
		UserTx newTx = new UserTx(myUser, new Date(), TxStatus.UNKNOWN, 0f);
		
		UserTx persistedNewTx = userTxRepository.save(newTx);
		
		
		
		return new ResponseEntity<ShoppingCartDto>(new ShoppingCartDto(persistedNewTx.getUserTxId()), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/getCart/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserTxDto> getCart(@PathVariable Long id) {
		
		 
		UserTx cart = userTxRepository.getOne(id);
		
		Set<UserTxItem> cartItems = cart.getItems();
		
		UserTxDto cartDto = new UserTxDto(cart.getUserTxId(), cart.getCreated(), cart.getStatus(), cart.getTotalAmount());  
		List<UserTxItemDto> cartItemsDto = new ArrayList<UserTxItemDto>();
		
		for(UserTxItem item : cartItems) {
			cartItemsDto.add(new UserTxItemDto(item.getUserTxItemId(), item.getPrice(), cartDto, item.getBuyingType(), item.getItemId()));
		}
		
		cartDto.setItems(cartItemsDto);
		
		return new ResponseEntity<UserTxDto>(cartDto, HttpStatus.OK);
	}
	
	
	@RequestMapping(path = "/getUserTxs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserTxDto>> getUserTxs() {
		
		//privremeno 
		UserA user = userRepo2.getOne(1l);
		List<UserTx> userTxs = user.getUserTxs().stream().filter(tx -> tx.getStatus() == TxStatus.SUCCESS).collect(Collectors.toList());
		List<UserTxDto> userTxDtos = new ArrayList<UserTxDto>();
		
		for(UserTx tx: userTxs) {
			
			Set<UserTxItem> cartItems = tx.getItems();
			
			UserTxDto cartDto = new UserTxDto(tx.getUserTxId(), tx.getCreated(), tx.getStatus(), tx.getTotalAmount());  
			List<UserTxItemDto> cartItemsDto = new ArrayList<UserTxItemDto>();
			
			for(UserTxItem item : cartItems) {
				Article a = articleRepo.getOne(item.getItemId());
				
				ArticleDto aDto = new ArticleDto(a.getArticleId(), a.getArticleTitle(), a.getArticleAbstract(), a.getPublishingDate(), a.getArticlePrice(), "", a.getDoi());
				UserTxItemDto d = new UserTxItemDto(item.getUserTxItemId(), item.getPrice(), cartDto, item.getBuyingType(), item.getItemId());
				d.setContent(aDto);
				
				cartItemsDto.add(d);
			}
			
			cartDto.setItems(cartItemsDto);
			
			userTxDtos.add(cartDto);

		}
		
				
		return new ResponseEntity<List<UserTxDto>>(userTxDtos, HttpStatus.OK);
	}
	
	public String getDocument(byte[] data) {
		String encoded = Base64Utility.encode(data);
//		 String ret = "data:base64" + "," + encoded;
		 String ret = encoded;
		
		return ret;
	}
}
