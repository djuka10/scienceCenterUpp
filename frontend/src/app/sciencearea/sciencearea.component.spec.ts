import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScienceareaComponent } from './sciencearea.component';

describe('ScienceareaComponent', () => {
  let component: ScienceareaComponent;
  let fixture: ComponentFixture<ScienceareaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScienceareaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScienceareaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
