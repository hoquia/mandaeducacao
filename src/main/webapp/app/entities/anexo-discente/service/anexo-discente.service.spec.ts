import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAnexoDiscente } from '../anexo-discente.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../anexo-discente.test-samples';

import { AnexoDiscenteService, RestAnexoDiscente } from './anexo-discente.service';

const requireRestSample: RestAnexoDiscente = {
  ...sampleWithRequiredData,
  validade: sampleWithRequiredData.validade?.format(DATE_FORMAT),
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('AnexoDiscente Service', () => {
  let service: AnexoDiscenteService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnexoDiscente | IAnexoDiscente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AnexoDiscenteService);
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

    it('should create a AnexoDiscente', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const anexoDiscente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anexoDiscente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnexoDiscente', () => {
      const anexoDiscente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anexoDiscente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnexoDiscente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnexoDiscente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnexoDiscente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnexoDiscenteToCollectionIfMissing', () => {
      it('should add a AnexoDiscente to an empty array', () => {
        const anexoDiscente: IAnexoDiscente = sampleWithRequiredData;
        expectedResult = service.addAnexoDiscenteToCollectionIfMissing([], anexoDiscente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoDiscente);
      });

      it('should not add a AnexoDiscente to an array that contains it', () => {
        const anexoDiscente: IAnexoDiscente = sampleWithRequiredData;
        const anexoDiscenteCollection: IAnexoDiscente[] = [
          {
            ...anexoDiscente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnexoDiscenteToCollectionIfMissing(anexoDiscenteCollection, anexoDiscente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnexoDiscente to an array that doesn't contain it", () => {
        const anexoDiscente: IAnexoDiscente = sampleWithRequiredData;
        const anexoDiscenteCollection: IAnexoDiscente[] = [sampleWithPartialData];
        expectedResult = service.addAnexoDiscenteToCollectionIfMissing(anexoDiscenteCollection, anexoDiscente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoDiscente);
      });

      it('should add only unique AnexoDiscente to an array', () => {
        const anexoDiscenteArray: IAnexoDiscente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const anexoDiscenteCollection: IAnexoDiscente[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoDiscenteToCollectionIfMissing(anexoDiscenteCollection, ...anexoDiscenteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anexoDiscente: IAnexoDiscente = sampleWithRequiredData;
        const anexoDiscente2: IAnexoDiscente = sampleWithPartialData;
        expectedResult = service.addAnexoDiscenteToCollectionIfMissing([], anexoDiscente, anexoDiscente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoDiscente);
        expect(expectedResult).toContain(anexoDiscente2);
      });

      it('should accept null and undefined values', () => {
        const anexoDiscente: IAnexoDiscente = sampleWithRequiredData;
        expectedResult = service.addAnexoDiscenteToCollectionIfMissing([], null, anexoDiscente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoDiscente);
      });

      it('should return initial array if no AnexoDiscente is added', () => {
        const anexoDiscenteCollection: IAnexoDiscente[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoDiscenteToCollectionIfMissing(anexoDiscenteCollection, undefined, null);
        expect(expectedResult).toEqual(anexoDiscenteCollection);
      });
    });

    describe('compareAnexoDiscente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnexoDiscente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnexoDiscente(entity1, entity2);
        const compareResult2 = service.compareAnexoDiscente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnexoDiscente(entity1, entity2);
        const compareResult2 = service.compareAnexoDiscente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnexoDiscente(entity1, entity2);
        const compareResult2 = service.compareAnexoDiscente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
