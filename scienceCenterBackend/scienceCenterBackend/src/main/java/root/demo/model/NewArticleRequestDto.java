package root.demo.model;

import java.io.Serializable;
import java.util.List;

import org.camunda.bpm.engine.form.FormField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewArticleRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8848348204449356145L;
	
	private String taskId;
	private String processInstanceId;
	private List<FormField> formFields;
	
	private List<ScienceAreaDto> articleScienceAreas;
	private List<TermDto> articleTerms;
	private List<UserDto> coAuthors;

}
