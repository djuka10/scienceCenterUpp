import { Opinion } from './opinion';
import { NewArticle } from './new-article';


export class UpdateArticleChanges {

     newArticleRequestDto;   
     newAarticleResponseDto: NewArticle;
                                                 
     reviewersOpinion: Opinion[];  
     editorsOpinion: Opinion[];    
                                                 
    authorsMessage: Opinion;          

    constructor() {}
}