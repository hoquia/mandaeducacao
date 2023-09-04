import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AplicacaoReciboDetailComponent } from './aplicacao-recibo-detail.component';

describe('AplicacaoRecibo Management Detail Component', () => {
  let comp: AplicacaoReciboDetailComponent;
  let fixture: ComponentFixture<AplicacaoReciboDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AplicacaoReciboDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ aplicacaoRecibo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AplicacaoReciboDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AplicacaoReciboDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load aplicacaoRecibo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.aplicacaoRecibo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
