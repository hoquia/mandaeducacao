import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EnderecoDiscenteDetailComponent } from './endereco-discente-detail.component';

describe('EnderecoDiscente Management Detail Component', () => {
  let comp: EnderecoDiscenteDetailComponent;
  let fixture: ComponentFixture<EnderecoDiscenteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EnderecoDiscenteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ enderecoDiscente: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EnderecoDiscenteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EnderecoDiscenteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load enderecoDiscente on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.enderecoDiscente).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
