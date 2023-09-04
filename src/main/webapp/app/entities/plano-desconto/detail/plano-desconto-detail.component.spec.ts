import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlanoDescontoDetailComponent } from './plano-desconto-detail.component';

describe('PlanoDesconto Management Detail Component', () => {
  let comp: PlanoDescontoDetailComponent;
  let fixture: ComponentFixture<PlanoDescontoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlanoDescontoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ planoDesconto: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PlanoDescontoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlanoDescontoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load planoDesconto on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.planoDesconto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
