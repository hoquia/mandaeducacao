import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IInstituicaoEnsino } from '../instituicao-ensino.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../instituicao-ensino.test-samples';

import { InstituicaoEnsinoService, RestInstituicaoEnsino } from './instituicao-ensino.service';

const requireRestSample: RestInstituicaoEnsino = {
  ...sampleWithRequiredData,
  fundacao: sampleWithRequiredData.fundacao?.format(DATE_FORMAT),
};

describe('InstituicaoEnsino Service', () => {
  let service: InstituicaoEnsinoService;
  let httpMock: HttpTestingController;
  let expectedResult: IInstituicaoEnsino | IInstituicaoEnsino[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InstituicaoEnsinoService);
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

    it('should create a InstituicaoEnsino', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const instituicaoEnsino = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(instituicaoEnsino).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InstituicaoEnsino', () => {
      const instituicaoEnsino = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(instituicaoEnsino).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InstituicaoEnsino', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InstituicaoEnsino', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a InstituicaoEnsino', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInstituicaoEnsinoToCollectionIfMissing', () => {
      it('should add a InstituicaoEnsino to an empty array', () => {
        const instituicaoEnsino: IInstituicaoEnsino = sampleWithRequiredData;
        expectedResult = service.addInstituicaoEnsinoToCollectionIfMissing([], instituicaoEnsino);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(instituicaoEnsino);
      });

      it('should not add a InstituicaoEnsino to an array that contains it', () => {
        const instituicaoEnsino: IInstituicaoEnsino = sampleWithRequiredData;
        const instituicaoEnsinoCollection: IInstituicaoEnsino[] = [
          {
            ...instituicaoEnsino,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInstituicaoEnsinoToCollectionIfMissing(instituicaoEnsinoCollection, instituicaoEnsino);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InstituicaoEnsino to an array that doesn't contain it", () => {
        const instituicaoEnsino: IInstituicaoEnsino = sampleWithRequiredData;
        const instituicaoEnsinoCollection: IInstituicaoEnsino[] = [sampleWithPartialData];
        expectedResult = service.addInstituicaoEnsinoToCollectionIfMissing(instituicaoEnsinoCollection, instituicaoEnsino);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(instituicaoEnsino);
      });

      it('should add only unique InstituicaoEnsino to an array', () => {
        const instituicaoEnsinoArray: IInstituicaoEnsino[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const instituicaoEnsinoCollection: IInstituicaoEnsino[] = [sampleWithRequiredData];
        expectedResult = service.addInstituicaoEnsinoToCollectionIfMissing(instituicaoEnsinoCollection, ...instituicaoEnsinoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const instituicaoEnsino: IInstituicaoEnsino = sampleWithRequiredData;
        const instituicaoEnsino2: IInstituicaoEnsino = sampleWithPartialData;
        expectedResult = service.addInstituicaoEnsinoToCollectionIfMissing([], instituicaoEnsino, instituicaoEnsino2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(instituicaoEnsino);
        expect(expectedResult).toContain(instituicaoEnsino2);
      });

      it('should accept null and undefined values', () => {
        const instituicaoEnsino: IInstituicaoEnsino = sampleWithRequiredData;
        expectedResult = service.addInstituicaoEnsinoToCollectionIfMissing([], null, instituicaoEnsino, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(instituicaoEnsino);
      });

      it('should return initial array if no InstituicaoEnsino is added', () => {
        const instituicaoEnsinoCollection: IInstituicaoEnsino[] = [sampleWithRequiredData];
        expectedResult = service.addInstituicaoEnsinoToCollectionIfMissing(instituicaoEnsinoCollection, undefined, null);
        expect(expectedResult).toEqual(instituicaoEnsinoCollection);
      });
    });

    describe('compareInstituicaoEnsino', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInstituicaoEnsino(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInstituicaoEnsino(entity1, entity2);
        const compareResult2 = service.compareInstituicaoEnsino(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInstituicaoEnsino(entity1, entity2);
        const compareResult2 = service.compareInstituicaoEnsino(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInstituicaoEnsino(entity1, entity2);
        const compareResult2 = service.compareInstituicaoEnsino(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
