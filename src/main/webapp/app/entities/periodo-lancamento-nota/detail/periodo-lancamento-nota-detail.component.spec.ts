import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeriodoLancamentoNotaDetailComponent } from './periodo-lancamento-nota-detail.component';

describe('PeriodoLancamentoNota Management Detail Component', () => {
  let comp: PeriodoLancamentoNotaDetailComponent;
  let fixture: ComponentFixture<PeriodoLancamentoNotaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PeriodoLancamentoNotaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ periodoLancamentoNota: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PeriodoLancamentoNotaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PeriodoLancamentoNotaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load periodoLancamentoNota on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.periodoLancamentoNota).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
