import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TransferenciaTurmaDetailComponent } from './transferencia-turma-detail.component';

describe('TransferenciaTurma Management Detail Component', () => {
  let comp: TransferenciaTurmaDetailComponent;
  let fixture: ComponentFixture<TransferenciaTurmaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransferenciaTurmaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transferenciaTurma: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TransferenciaTurmaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TransferenciaTurmaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transferenciaTurma on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transferenciaTurma).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
