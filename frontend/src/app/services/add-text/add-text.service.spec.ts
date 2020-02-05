import { TestBed, inject } from '@angular/core/testing';

import { AddTextService } from './add-text.service';

describe('AddTextService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AddTextService]
    });
  });

  it('should be created', inject([AddTextService], (service: AddTextService) => {
    expect(service).toBeTruthy();
  }));
});
