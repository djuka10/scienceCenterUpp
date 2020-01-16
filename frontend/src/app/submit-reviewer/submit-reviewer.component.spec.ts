import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitReviewerComponent } from './submit-reviewer.component';

describe('SubmitReviewerComponent', () => {
  let component: SubmitReviewerComponent;
  let fixture: ComponentFixture<SubmitReviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitReviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitReviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
