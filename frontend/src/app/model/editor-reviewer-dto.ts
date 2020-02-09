import { ScienceArea } from './science-area';
import { Magazine } from './magazine';
import { User } from '../guard/user';

export class EditorReviewer {

    editorByScArId: number;
	
	editor: boolean;
	
	editorReviewer: User;
	
    scienceArea: ScienceArea;
	
	magazine: Magazine;

    constructor(){}
}