package root.demo.model.repo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class EditorByScienceArea {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long editorByScArId;
	@OneToOne
	private MyUser editor;
	@OneToOne
	private ScienceArea scienceArea;
	public EditorByScienceArea() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditorByScienceArea(Long editorByScArId, MyUser editor) {
		super();
		this.editorByScArId = editorByScArId;
		this.editor = editor;

	}
	public Long getEditorByScArId() {
		return editorByScArId;
	}
	public void setEditorByScArId(Long editorByScArId) {
		this.editorByScArId = editorByScArId;
	}
	public MyUser getEditor() {
		return editor;
	}
	public void setEditor(MyUser editor) {
		this.editor = editor;
	}
	public ScienceArea getScienceArea() {
		return scienceArea;
	}
	public void setScienceArea(ScienceArea scienceArea) {
		this.scienceArea = scienceArea;
	}
	
	
	

}
