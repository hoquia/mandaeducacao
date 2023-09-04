import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlanoCurricularDetailComponent } from './plano-curricular-detail.component';

describe('PlanoCurricular Management Detail Component', () => {
  let comp: PlanoCurricularDetailComponent;
  let fixture: ComponentFixture<PlanoCurricularDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlanoCurricularDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ planoCurricular: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PlanoCurricularDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlanoCurricularDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load planoCurricular on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.planoCurricular).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
