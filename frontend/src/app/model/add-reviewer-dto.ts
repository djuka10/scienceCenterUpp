import { EditorReviewer } from './editor-reviewer-dto';
import { Magazine } from './magazine';
import { Article } from './article';

export class AddReviewerDto {

     taskId: string;
     processInstanceId: string;

     articleDto: Article;                                   
     magazineDto: Magazine;                                 
     editorsReviewersDto: EditorReviewer[];
                                                         
     subProcessMfExecutionId: string;
     insideMf: boolean;                          

    constructor() {}
}