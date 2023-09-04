import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EstadoDisciplinaCurricularDetailComponent } from './estado-disciplina-curricular-detail.component';

describe('EstadoDisciplinaCurricular Management Detail Component', () => {
  let comp: EstadoDisciplinaCurricularDetailComponent;
  let fixture: ComponentFixture<EstadoDisciplinaCurricularDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstadoDisciplinaCurricularDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ estadoDisciplinaCurricular: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EstadoDisciplinaCurricularDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EstadoDisciplinaCurricularDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load estadoDisciplinaCurricular on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.estadoDisciplinaCurricular).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
