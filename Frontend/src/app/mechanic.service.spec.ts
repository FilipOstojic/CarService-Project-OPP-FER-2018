import { TestBed } from '@angular/core/testing';

import { MechanicService } from './mechanic.service';

describe('MechanicService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MechanicService = TestBed.get(MechanicService);
    expect(service).toBeTruthy();
  });
});
