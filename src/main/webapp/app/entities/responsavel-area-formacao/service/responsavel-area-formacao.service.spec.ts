import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IResponsavelAreaFormacao } from '../responsavel-area-formacao.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../responsavel-area-formacao.test-samples';

import { ResponsavelAreaFormacaoService, RestResponsavelAreaFormacao } from './responsavel-area-formacao.service';

const requireRestSample: RestResponsavelAreaFormacao = {
  ...sampleWithRequiredData,
  de: sampleWithRequiredData.de?.format(DATE_FORMAT),
  ate: sampleWithRequiredData.ate?.format(DATE_FORMAT),
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('ResponsavelAreaFormacao Service', () => {
  let service: ResponsavelAreaFormacaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IResponsavelAreaFormacao | IResponsavelAreaFormacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResponsavelAreaFormacaoService);
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

    it('should create a ResponsavelAreaFormacao', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const responsavelAreaFormacao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(responsavelAreaFormacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResponsavelAreaFormacao', () => {
      const responsavelAreaFormacao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(responsavelAreaFormacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResponsavelAreaFormacao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResponsavelAreaFormacao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResponsavelAreaFormacao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResponsavelAreaFormacaoToCollectionIfMissing', () => {
      it('should add a ResponsavelAreaFormacao to an empty array', () => {
        const responsavelAreaFormacao: IResponsavelAreaFormacao = sampleWithRequiredData;
        expectedResult = service.addResponsavelAreaFormacaoToCollectionIfMissing([], responsavelAreaFormacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(responsavelAreaFormacao);
      });

      it('should not add a ResponsavelAreaFormacao to an array that contains it', () => {
        const responsavelAreaFormacao: IResponsavelAreaFormacao = sampleWithRequiredData;
        const responsavelAreaFormacaoCollection: IResponsavelAreaFormacao[] = [
          {
            ...responsavelAreaFormacao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResponsavelAreaFormacaoToCollectionIfMissing(
          responsavelAreaFormacaoCollection,
          responsavelAreaFormacao
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResponsavelAreaFormacao to an array that doesn't contain it", () => {
        const responsavelAreaFormacao: IResponsavelAreaFormacao = sampleWithRequiredData;
        const responsavelAreaFormacaoCollection: IResponsavelAreaFormacao[] = [sampleWithPartialData];
        expectedResult = service.addResponsavelAreaFormacaoToCollectionIfMissing(
          responsavelAreaFormacaoCollection,
          responsavelAreaFormacao
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(responsavelAreaFormacao);
      });

      it('should add only unique ResponsavelAreaFormacao to an array', () => {
        const responsavelAreaFormacaoArray: IResponsavelAreaFormacao[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const responsavelAreaFormacaoCollection: IResponsavelAreaFormacao[] = [sampleWithRequiredData];
        expectedResult = service.addResponsavelAreaFormacaoToCollectionIfMissing(
          responsavelAreaFormacaoCollection,
          ...responsavelAreaFormacaoArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const responsavelAreaFormacao: IResponsavelAreaFormacao = sampleWithRequiredData;
        const responsavelAreaFormacao2: IResponsavelAreaFormacao = sampleWithPartialData;
        expectedResult = service.addResponsavelAreaFormacaoToCollectionIfMissing([], responsavelAreaFormacao, responsavelAreaFormacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(responsavelAreaFormacao);
        expect(expectedResult).toContain(responsavelAreaFormacao2);
      });

      it('should accept null and undefined values', () => {
        const responsavelAreaFormacao: IResponsavelAreaFormacao = sampleWithRequiredData;
        expectedResult = service.addResponsavelAreaFormacaoToCollectionIfMissing([], null, responsavelAreaFormacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(responsavelAreaFormacao);
      });

      it('should return initial array if no ResponsavelAreaFormacao is added', () => {
        const responsavelAreaFormacaoCollection: IResponsavelAreaFormacao[] = [sampleWithRequiredData];
        expectedResult = service.addResponsavelAreaFormacaoToCollectionIfMissing(responsavelAreaFormacaoCollection, undefined, null);
        expect(expectedResult).toEqual(responsavelAreaFormacaoCollection);
      });
    });

    describe('compareResponsavelAreaFormacao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResponsavelAreaFormacao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareResponsavelAreaFormacao(entity1, entity2);
        const compareResult2 = service.compareResponsavelAreaFormacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareResponsavelAreaFormacao(entity1, entity2);
        const compareResult2 = service.compareResponsavelAreaFormacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareResponsavelAreaFormacao(entity1, entity2);
        const compareResult2 = service.compareResponsavelAreaFormacao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
