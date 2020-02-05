package root.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2678019881969247829L;
	
	private String taskId;
	private String processInstanceId;
	
	private Long articleId;
	private String articleTitle;
	private String articleAbstract;
	
	private ScienceAreaDto scienceArea;
	private Date publishingDate;
	
	private UserDto author;
	private List<UserDto> coAuthors;
	private List<TermDto> keyTerms;
	
	private Float price;
	
	private String file;
	//file, doi, edition, status

}
