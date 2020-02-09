package root.demo.model;

import java.io.Serializable;
import java.util.List;

import org.camunda.bpm.engine.form.FormField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.demo.model.repo.OpinionAboutArticle;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleChangesDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 7267623785811514588L;
	
	private String taskId;
	private String processInstanceId;
	private List<FormField> formFields;
	
	private List<ScienceAreaDto> articleScienceAreas;
	private List<TermDto> articleTerms;
	private List<UserDto> coAuthors;
	
	private NewArticleRequestDto newArticleRequestDto;
	private NewArticleResponseDto newAarticleResponseDto;
	
	private List<OpinionAboutArticle> reviewersOpinion;
	private List<OpinionAboutArticle> editorsOpinion;
	
	private OpinionAboutArticle authorsMessage;
}
