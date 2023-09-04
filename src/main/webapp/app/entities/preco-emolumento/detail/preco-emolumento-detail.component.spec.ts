import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrecoEmolumentoDetailComponent } from './preco-emolumento-detail.component';

describe('PrecoEmolumento Management Detail Component', () => {
  let comp: PrecoEmolumentoDetailComponent;
  let fixture: ComponentFixture<PrecoEmolumentoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrecoEmolumentoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ precoEmolumento: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PrecoEmolumentoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PrecoEmolumentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load precoEmolumento on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.precoEmolumento).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
