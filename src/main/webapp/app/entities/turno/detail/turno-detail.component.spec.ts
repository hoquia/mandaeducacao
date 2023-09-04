import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TurnoDetailComponent } from './turno-detail.component';

describe('Turno Management Detail Component', () => {
  let comp: TurnoDetailComponent;
  let fixture: ComponentFixture<TurnoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TurnoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ turno: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TurnoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TurnoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load turno on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.turno).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
