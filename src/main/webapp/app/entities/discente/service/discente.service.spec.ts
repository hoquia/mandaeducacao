import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDiscente } from '../discente.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../discente.test-samples';

import { DiscenteService, RestDiscente } from './discente.service';

const requireRestSample: RestDiscente = {
  ...sampleWithRequiredData,
  nascimento: sampleWithRequiredData.nascimento?.format(DATE_FORMAT),
  documentoEmissao: sampleWithRequiredData.documentoEmissao?.format(DATE_FORMAT),
  documentoValidade: sampleWithRequiredData.documentoValidade?.format(DATE_FORMAT),
  dataIngresso: sampleWithRequiredData.dataIngresso?.toJSON(),
};

describe('Discente Service', () => {
  let service: DiscenteService;
  let httpMock: HttpTestingController;
  let expectedResult: IDiscente | IDiscente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DiscenteService);
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

    it('should create a Discente', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const discente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(discente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Discente', () => {
      const discente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(discente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Discente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Discente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Discente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDiscenteToCollectionIfMissing', () => {
      it('should add a Discente to an empty array', () => {
        const discente: IDiscente = sampleWithRequiredData;
        expectedResult = service.addDiscenteToCollectionIfMissing([], discente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(discente);
      });

      it('should not add a Discente to an array that contains it', () => {
        const discente: IDiscente = sampleWithRequiredData;
        const discenteCollection: IDiscente[] = [
          {
            ...discente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDiscenteToCollectionIfMissing(discenteCollection, discente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Discente to an array that doesn't contain it", () => {
        const discente: IDiscente = sampleWithRequiredData;
        const discenteCollection: IDiscente[] = [sampleWithPartialData];
        expectedResult = service.addDiscenteToCollectionIfMissing(discenteCollection, discente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(discente);
      });

      it('should add only unique Discente to an array', () => {
        const discenteArray: IDiscente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const discenteCollection: IDiscente[] = [sampleWithRequiredData];
        expectedResult = service.addDiscenteToCollectionIfMissing(discenteCollection, ...discenteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const discente: IDiscente = sampleWithRequiredData;
        const discente2: IDiscente = sampleWithPartialData;
        expectedResult = service.addDiscenteToCollectionIfMissing([], discente, discente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(discente);
        expect(expectedResult).toContain(discente2);
      });

      it('should accept null and undefined values', () => {
        const discente: IDiscente = sampleWithRequiredData;
        expectedResult = service.addDiscenteToCollectionIfMissing([], null, discente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(discente);
      });

      it('should return initial array if no Discente is added', () => {
        const discenteCollection: IDiscente[] = [sampleWithRequiredData];
        expectedResult = service.addDiscenteToCollectionIfMissing(discenteCollection, undefined, null);
        expect(expectedResult).toEqual(discenteCollection);
      });
    });

    describe('compareDiscente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDiscente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDiscente(entity1, entity2);
        const compareResult2 = service.compareDiscente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDiscente(entity1, entity2);
        const compareResult2 = service.compareDiscente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDiscente(entity1, entity2);
        const compareResult2 = service.compareDiscente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
