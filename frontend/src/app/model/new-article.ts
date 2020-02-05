import { Term } from './term';
import { User } from '../guard/user';


export class NewArticle{

    articleTitle: string;
    articleAbstract: string;
    articleScienceArea: string;
    articlePrice: number;
    articleTerm: Term[] = [];
    articleCoAuthors: User[] = [];
    
    fileName: string;
    file: string | ArrayBuffer;

    constructor(){}
}