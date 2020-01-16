import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditorReviewerComponent } from './add-editor-reviewer.component';

describe('AddEditorReviewerComponent', () => {
  let component: AddEditorReviewerComponent;
  let fixture: ComponentFixture<AddEditorReviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEditorReviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEditorReviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
