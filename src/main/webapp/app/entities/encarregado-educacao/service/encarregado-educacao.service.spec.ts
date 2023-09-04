import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IEncarregadoEducacao } from '../encarregado-educacao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../encarregado-educacao.test-samples';

import { EncarregadoEducacaoService, RestEncarregadoEducacao } from './encarregado-educacao.service';

const requireRestSample: RestEncarregadoEducacao = {
  ...sampleWithRequiredData,
  nascimento: sampleWithRequiredData.nascimento?.format(DATE_FORMAT),
};

describe('EncarregadoEducacao Service', () => {
  let service: EncarregadoEducacaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IEncarregadoEducacao | IEncarregadoEducacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EncarregadoEducacaoService);
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

    it('should create a EncarregadoEducacao', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const encarregadoEducacao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(encarregadoEducacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EncarregadoEducacao', () => {
      const encarregadoEducacao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(encarregadoEducacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EncarregadoEducacao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EncarregadoEducacao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EncarregadoEducacao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEncarregadoEducacaoToCollectionIfMissing', () => {
      it('should add a EncarregadoEducacao to an empty array', () => {
        const encarregadoEducacao: IEncarregadoEducacao = sampleWithRequiredData;
        expectedResult = service.addEncarregadoEducacaoToCollectionIfMissing([], encarregadoEducacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(encarregadoEducacao);
      });

      it('should not add a EncarregadoEducacao to an array that contains it', () => {
        const encarregadoEducacao: IEncarregadoEducacao = sampleWithRequiredData;
        const encarregadoEducacaoCollection: IEncarregadoEducacao[] = [
          {
            ...encarregadoEducacao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEncarregadoEducacaoToCollectionIfMissing(encarregadoEducacaoCollection, encarregadoEducacao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EncarregadoEducacao to an array that doesn't contain it", () => {
        const encarregadoEducacao: IEncarregadoEducacao = sampleWithRequiredData;
        const encarregadoEducacaoCollection: IEncarregadoEducacao[] = [sampleWithPartialData];
        expectedResult = service.addEncarregadoEducacaoToCollectionIfMissing(encarregadoEducacaoCollection, encarregadoEducacao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(encarregadoEducacao);
      });

      it('should add only unique EncarregadoEducacao to an array', () => {
        const encarregadoEducacaoArray: IEncarregadoEducacao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const encarregadoEducacaoCollection: IEncarregadoEducacao[] = [sampleWithRequiredData];
        expectedResult = service.addEncarregadoEducacaoToCollectionIfMissing(encarregadoEducacaoCollection, ...encarregadoEducacaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const encarregadoEducacao: IEncarregadoEducacao = sampleWithRequiredData;
        const encarregadoEducacao2: IEncarregadoEducacao = sampleWithPartialData;
        expectedResult = service.addEncarregadoEducacaoToCollectionIfMissing([], encarregadoEducacao, encarregadoEducacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(encarregadoEducacao);
        expect(expectedResult).toContain(encarregadoEducacao2);
      });

      it('should accept null and undefined values', () => {
        const encarregadoEducacao: IEncarregadoEducacao = sampleWithRequiredData;
        expectedResult = service.addEncarregadoEducacaoToCollectionIfMissing([], null, encarregadoEducacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(encarregadoEducacao);
      });

      it('should return initial array if no EncarregadoEducacao is added', () => {
        const encarregadoEducacaoCollection: IEncarregadoEducacao[] = [sampleWithRequiredData];
        expectedResult = service.addEncarregadoEducacaoToCollectionIfMissing(encarregadoEducacaoCollection, undefined, null);
        expect(expectedResult).toEqual(encarregadoEducacaoCollection);
      });
    });

    describe('compareEncarregadoEducacao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEncarregadoEducacao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEncarregadoEducacao(entity1, entity2);
        const compareResult2 = service.compareEncarregadoEducacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEncarregadoEducacao(entity1, entity2);
        const compareResult2 = service.compareEncarregadoEducacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEncarregadoEducacao(entity1, entity2);
        const compareResult2 = service.compareEncarregadoEducacao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
