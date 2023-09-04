import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeriodoHorarioDetailComponent } from './periodo-horario-detail.component';

describe('PeriodoHorario Management Detail Component', () => {
  let comp: PeriodoHorarioDetailComponent;
  let fixture: ComponentFixture<PeriodoHorarioDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PeriodoHorarioDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ periodoHorario: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PeriodoHorarioDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PeriodoHorarioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load periodoHorario on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.periodoHorario).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
