package root.demo.model;

import java.io.Serializable;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.demo.model.repo.OpinionAboutArticle;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewingEditorDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8111994278994400192L;
	
	private ArticleDto article;
	private MagazineDto magazine;
	private List<OpinionAboutArticle> opinions;
	
	private List<OpinionAboutArticle> authorsMessages;

	
	private OpinionAboutArticle editorOpinion;

}
