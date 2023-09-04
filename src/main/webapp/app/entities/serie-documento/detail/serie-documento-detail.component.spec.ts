import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SerieDocumentoDetailComponent } from './serie-documento-detail.component';

describe('SerieDocumento Management Detail Component', () => {
  let comp: SerieDocumentoDetailComponent;
  let fixture: ComponentFixture<SerieDocumentoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SerieDocumentoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ serieDocumento: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SerieDocumentoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SerieDocumentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load serieDocumento on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.serieDocumento).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
