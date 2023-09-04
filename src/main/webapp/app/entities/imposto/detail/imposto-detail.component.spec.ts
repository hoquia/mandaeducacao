import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImpostoDetailComponent } from './imposto-detail.component';

describe('Imposto Management Detail Component', () => {
  let comp: ImpostoDetailComponent;
  let fixture: ComponentFixture<ImpostoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ImpostoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ imposto: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ImpostoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ImpostoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load imposto on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.imposto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
