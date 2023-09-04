import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NotasGeralDisciplinaDetailComponent } from './notas-geral-disciplina-detail.component';

describe('NotasGeralDisciplina Management Detail Component', () => {
  let comp: NotasGeralDisciplinaDetailComponent;
  let fixture: ComponentFixture<NotasGeralDisciplinaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotasGeralDisciplinaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ notasGeralDisciplina: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NotasGeralDisciplinaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NotasGeralDisciplinaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load notasGeralDisciplina on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.notasGeralDisciplina).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
