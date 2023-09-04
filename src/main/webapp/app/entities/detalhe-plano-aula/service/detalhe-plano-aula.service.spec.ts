import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDetalhePlanoAula } from '../detalhe-plano-aula.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../detalhe-plano-aula.test-samples';

import { DetalhePlanoAulaService } from './detalhe-plano-aula.service';

const requireRestSample: IDetalhePlanoAula = {
  ...sampleWithRequiredData,
};

describe('DetalhePlanoAula Service', () => {
  let service: DetalhePlanoAulaService;
  let httpMock: HttpTestingController;
  let expectedResult: IDetalhePlanoAula | IDetalhePlanoAula[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DetalhePlanoAulaService);
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

    it('should create a DetalhePlanoAula', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const detalhePlanoAula = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(detalhePlanoAula).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DetalhePlanoAula', () => {
      const detalhePlanoAula = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(detalhePlanoAula).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DetalhePlanoAula', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DetalhePlanoAula', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DetalhePlanoAula', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDetalhePlanoAulaToCollectionIfMissing', () => {
      it('should add a DetalhePlanoAula to an empty array', () => {
        const detalhePlanoAula: IDetalhePlanoAula = sampleWithRequiredData;
        expectedResult = service.addDetalhePlanoAulaToCollectionIfMissing([], detalhePlanoAula);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detalhePlanoAula);
      });

      it('should not add a DetalhePlanoAula to an array that contains it', () => {
        const detalhePlanoAula: IDetalhePlanoAula = sampleWithRequiredData;
        const detalhePlanoAulaCollection: IDetalhePlanoAula[] = [
          {
            ...detalhePlanoAula,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDetalhePlanoAulaToCollectionIfMissing(detalhePlanoAulaCollection, detalhePlanoAula);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DetalhePlanoAula to an array that doesn't contain it", () => {
        const detalhePlanoAula: IDetalhePlanoAula = sampleWithRequiredData;
        const detalhePlanoAulaCollection: IDetalhePlanoAula[] = [sampleWithPartialData];
        expectedResult = service.addDetalhePlanoAulaToCollectionIfMissing(detalhePlanoAulaCollection, detalhePlanoAula);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detalhePlanoAula);
      });

      it('should add only unique DetalhePlanoAula to an array', () => {
        const detalhePlanoAulaArray: IDetalhePlanoAula[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const detalhePlanoAulaCollection: IDetalhePlanoAula[] = [sampleWithRequiredData];
        expectedResult = service.addDetalhePlanoAulaToCollectionIfMissing(detalhePlanoAulaCollection, ...detalhePlanoAulaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const detalhePlanoAula: IDetalhePlanoAula = sampleWithRequiredData;
        const detalhePlanoAula2: IDetalhePlanoAula = sampleWithPartialData;
        expectedResult = service.addDetalhePlanoAulaToCollectionIfMissing([], detalhePlanoAula, detalhePlanoAula2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(detalhePlanoAula);
        expect(expectedResult).toContain(detalhePlanoAula2);
      });

      it('should accept null and undefined values', () => {
        const detalhePlanoAula: IDetalhePlanoAula = sampleWithRequiredData;
        expectedResult = service.addDetalhePlanoAulaToCollectionIfMissing([], null, detalhePlanoAula, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(detalhePlanoAula);
      });

      it('should return initial array if no DetalhePlanoAula is added', () => {
        const detalhePlanoAulaCollection: IDetalhePlanoAula[] = [sampleWithRequiredData];
        expectedResult = service.addDetalhePlanoAulaToCollectionIfMissing(detalhePlanoAulaCollection, undefined, null);
        expect(expectedResult).toEqual(detalhePlanoAulaCollection);
      });
    });

    describe('compareDetalhePlanoAula', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDetalhePlanoAula(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDetalhePlanoAula(entity1, entity2);
        const compareResult2 = service.compareDetalhePlanoAula(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDetalhePlanoAula(entity1, entity2);
        const compareResult2 = service.compareDetalhePlanoAula(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDetalhePlanoAula(entity1, entity2);
        const compareResult2 = service.compareDetalhePlanoAula(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
