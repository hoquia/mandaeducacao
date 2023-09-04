import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResumoImpostoFacturaDetailComponent } from './resumo-imposto-factura-detail.component';

describe('ResumoImpostoFactura Management Detail Component', () => {
  let comp: ResumoImpostoFacturaDetailComponent;
  let fixture: ComponentFixture<ResumoImpostoFacturaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResumoImpostoFacturaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resumoImpostoFactura: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResumoImpostoFacturaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResumoImpostoFacturaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resumoImpostoFactura on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resumoImpostoFactura).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
