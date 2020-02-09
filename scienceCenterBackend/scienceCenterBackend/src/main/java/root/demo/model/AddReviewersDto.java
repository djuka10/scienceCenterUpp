package root.demo.model;

import java.io.Serializable;
import java.util.List;

import org.camunda.bpm.engine.form.FormField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewersDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1643781589019222610L;
	
	private String taskId;
	private String processInstanceId;
	private List<FormField> formFields;
	
	private ArticleDto articleDto;
	private MagazineDto magazineDto;
	private List<EditorReviewerByScienceAreaDto> editorsReviewersDto;

	private String subProcessMfExecutionId;
	private boolean insideMf;

}
