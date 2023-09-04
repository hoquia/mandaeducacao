import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MedidaDisciplinarDetailComponent } from './medida-disciplinar-detail.component';

describe('MedidaDisciplinar Management Detail Component', () => {
  let comp: MedidaDisciplinarDetailComponent;
  let fixture: ComponentFixture<MedidaDisciplinarDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MedidaDisciplinarDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ medidaDisciplinar: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MedidaDisciplinarDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MedidaDisciplinarDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load medidaDisciplinar on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.medidaDisciplinar).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
