import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlanoMultaDetailComponent } from './plano-multa-detail.component';

describe('PlanoMulta Management Detail Component', () => {
  let comp: PlanoMultaDetailComponent;
  let fixture: ComponentFixture<PlanoMultaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlanoMultaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ planoMulta: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PlanoMultaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlanoMultaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load planoMulta on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.planoMulta).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
