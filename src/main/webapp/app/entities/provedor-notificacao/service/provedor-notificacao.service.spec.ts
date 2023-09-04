import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProvedorNotificacao } from '../provedor-notificacao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../provedor-notificacao.test-samples';

import { ProvedorNotificacaoService } from './provedor-notificacao.service';

const requireRestSample: IProvedorNotificacao = {
  ...sampleWithRequiredData,
};

describe('ProvedorNotificacao Service', () => {
  let service: ProvedorNotificacaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IProvedorNotificacao | IProvedorNotificacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProvedorNotificacaoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ProvedorNotificacao', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const provedorNotificacao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(provedorNotificacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProvedorNotificacao', () => {
      const provedorNotificacao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(provedorNotificacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProvedorNotificacao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProvedorNotificacao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProvedorNotificacao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProvedorNotificacaoToCollectionIfMissing', () => {
      it('should add a ProvedorNotificacao to an empty array', () => {
        const provedorNotificacao: IProvedorNotificacao = sampleWithRequiredData;
        expectedResult = service.addProvedorNotificacaoToCollectionIfMissing([], provedorNotificacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(provedorNotificacao);
      });

      it('should not add a ProvedorNotificacao to an array that contains it', () => {
        const provedorNotificacao: IProvedorNotificacao = sampleWithRequiredData;
        const provedorNotificacaoCollection: IProvedorNotificacao[] = [
          {
            ...provedorNotificacao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProvedorNotificacaoToCollectionIfMissing(provedorNotificacaoCollection, provedorNotificacao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProvedorNotificacao to an array that doesn't contain it", () => {
        const provedorNotificacao: IProvedorNotificacao = sampleWithRequiredData;
        const provedorNotificacaoCollection: IProvedorNotificacao[] = [sampleWithPartialData];
        expectedResult = service.addProvedorNotificacaoToCollectionIfMissing(provedorNotificacaoCollection, provedorNotificacao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(provedorNotificacao);
      });

      it('should add only unique ProvedorNotificacao to an array', () => {
        const provedorNotificacaoArray: IProvedorNotificacao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const provedorNotificacaoCollection: IProvedorNotificacao[] = [sampleWithRequiredData];
        expectedResult = service.addProvedorNotificacaoToCollectionIfMissing(provedorNotificacaoCollection, ...provedorNotificacaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const provedorNotificacao: IProvedorNotificacao = sampleWithRequiredData;
        const provedorNotificacao2: IProvedorNotificacao = sampleWithPartialData;
        expectedResult = service.addProvedorNotificacaoToCollectionIfMissing([], provedorNotificacao, provedorNotificacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(provedorNotificacao);
        expect(expectedResult).toContain(provedorNotificacao2);
      });

      it('should accept null and undefined values', () => {
        const provedorNotificacao: IProvedorNotificacao = sampleWithRequiredData;
        expectedResult = service.addProvedorNotificacaoToCollectionIfMissing([], null, provedorNotificacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(provedorNotificacao);
      });

      it('should return initial array if no ProvedorNotificacao is added', () => {
        const provedorNotificacaoCollection: IProvedorNotificacao[] = [sampleWithRequiredData];
        expectedResult = service.addProvedorNotificacaoToCollectionIfMissing(provedorNotificacaoCollection, undefined, null);
        expect(expectedResult).toEqual(provedorNotificacaoCollection);
      });
    });

    describe('compareProvedorNotificacao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProvedorNotificacao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProvedorNotificacao(entity1, entity2);
        const compareResult2 = service.compareProvedorNotificacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProvedorNotificacao(entity1, entity2);
        const compareResult2 = service.compareProvedorNotificacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProvedorNotificacao(entity1, entity2);
        const compareResult2 = service.compareProvedorNotificacao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
