import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocumentoComercialDetailComponent } from './documento-comercial-detail.component';

describe('DocumentoComercial Management Detail Component', () => {
  let comp: DocumentoComercialDetailComponent;
  let fixture: ComponentFixture<DocumentoComercialDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DocumentoComercialDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ documentoComercial: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DocumentoComercialDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DocumentoComercialDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load documentoComercial on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.documentoComercial).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
