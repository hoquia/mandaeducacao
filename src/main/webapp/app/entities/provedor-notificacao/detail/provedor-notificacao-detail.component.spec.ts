import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvedorNotificacaoDetailComponent } from './provedor-notificacao-detail.component';

describe('ProvedorNotificacao Management Detail Component', () => {
  let comp: ProvedorNotificacaoDetailComponent;
  let fixture: ComponentFixture<ProvedorNotificacaoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProvedorNotificacaoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ provedorNotificacao: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProvedorNotificacaoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProvedorNotificacaoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load provedorNotificacao on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.provedorNotificacao).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
