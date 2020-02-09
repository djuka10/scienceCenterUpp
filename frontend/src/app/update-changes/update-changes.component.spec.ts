import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateChangesComponent } from './update-changes.component';

describe('UpdateChangesComponent', () => {
  let component: UpdateChangesComponent;
  let fixture: ComponentFixture<UpdateChangesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateChangesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateChangesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
