import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DisciplinaCurricularDetailComponent } from './disciplina-curricular-detail.component';

describe('DisciplinaCurricular Management Detail Component', () => {
  let comp: DisciplinaCurricularDetailComponent;
  let fixture: ComponentFixture<DisciplinaCurricularDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DisciplinaCurricularDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ disciplinaCurricular: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DisciplinaCurricularDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DisciplinaCurricularDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load disciplinaCurricular on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.disciplinaCurricular).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
