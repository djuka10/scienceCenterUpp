import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalizePdfComponent } from './analize-pdf.component';

describe('AnalizePdfComponent', () => {
  let component: AnalizePdfComponent;
  let fixture: ComponentFixture<AnalizePdfComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnalizePdfComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnalizePdfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
