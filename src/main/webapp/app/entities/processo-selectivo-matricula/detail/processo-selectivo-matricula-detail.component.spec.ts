import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProcessoSelectivoMatriculaDetailComponent } from './processo-selectivo-matricula-detail.component';

describe('ProcessoSelectivoMatricula Management Detail Component', () => {
  let comp: ProcessoSelectivoMatriculaDetailComponent;
  let fixture: ComponentFixture<ProcessoSelectivoMatriculaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProcessoSelectivoMatriculaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ processoSelectivoMatricula: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProcessoSelectivoMatriculaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProcessoSelectivoMatriculaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load processoSelectivoMatricula on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.processoSelectivoMatricula).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
