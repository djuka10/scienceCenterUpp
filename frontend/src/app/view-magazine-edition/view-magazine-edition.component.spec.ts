import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMagazineEditionComponent } from './view-magazine-edition.component';

describe('ViewMagazineEditionComponent', () => {
  let component: ViewMagazineEditionComponent;
  let fixture: ComponentFixture<ViewMagazineEditionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewMagazineEditionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewMagazineEditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
