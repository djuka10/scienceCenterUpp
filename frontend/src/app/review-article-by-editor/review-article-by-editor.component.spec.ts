import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewArticleByEditorComponent } from './review-article-by-editor.component';

describe('ReviewArticleByEditorComponent', () => {
  let component: ReviewArticleByEditorComponent;
  let fixture: ComponentFixture<ReviewArticleByEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewArticleByEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewArticleByEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
