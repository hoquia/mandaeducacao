import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NotasPeriodicaDisciplinaDetailComponent } from './notas-periodica-disciplina-detail.component';

describe('NotasPeriodicaDisciplina Management Detail Component', () => {
  let comp: NotasPeriodicaDisciplinaDetailComponent;
  let fixture: ComponentFixture<NotasPeriodicaDisciplinaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotasPeriodicaDisciplinaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ notasPeriodicaDisciplina: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NotasPeriodicaDisciplinaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NotasPeriodicaDisciplinaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load notasPeriodicaDisciplina on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.notasPeriodicaDisciplina).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
