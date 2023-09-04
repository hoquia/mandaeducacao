import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AnoLectivoDetailComponent } from './ano-lectivo-detail.component';

describe('AnoLectivo Management Detail Component', () => {
  let comp: AnoLectivoDetailComponent;
  let fixture: ComponentFixture<AnoLectivoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AnoLectivoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ anoLectivo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AnoLectivoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnoLectivoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load anoLectivo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.anoLectivo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
