import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LookupItemDetailComponent } from './lookup-item-detail.component';

describe('LookupItem Management Detail Component', () => {
  let comp: LookupItemDetailComponent;
  let fixture: ComponentFixture<LookupItemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LookupItemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ lookupItem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LookupItemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LookupItemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load lookupItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.lookupItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
