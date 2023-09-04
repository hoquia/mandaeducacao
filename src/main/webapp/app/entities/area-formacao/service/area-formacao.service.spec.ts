import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAreaFormacao } from '../area-formacao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../area-formacao.test-samples';

import { AreaFormacaoService } from './area-formacao.service';

const requireRestSample: IAreaFormacao = {
  ...sampleWithRequiredData,
};

describe('AreaFormacao Service', () => {
  let service: AreaFormacaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAreaFormacao | IAreaFormacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AreaFormacaoService);
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

    it('should create a AreaFormacao', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const areaFormacao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(areaFormacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AreaFormacao', () => {
      const areaFormacao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(areaFormacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AreaFormacao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AreaFormacao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AreaFormacao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAreaFormacaoToCollectionIfMissing', () => {
      it('should add a AreaFormacao to an empty array', () => {
        const areaFormacao: IAreaFormacao = sampleWithRequiredData;
        expectedResult = service.addAreaFormacaoToCollectionIfMissing([], areaFormacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaFormacao);
      });

      it('should not add a AreaFormacao to an array that contains it', () => {
        const areaFormacao: IAreaFormacao = sampleWithRequiredData;
        const areaFormacaoCollection: IAreaFormacao[] = [
          {
            ...areaFormacao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAreaFormacaoToCollectionIfMissing(areaFormacaoCollection, areaFormacao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AreaFormacao to an array that doesn't contain it", () => {
        const areaFormacao: IAreaFormacao = sampleWithRequiredData;
        const areaFormacaoCollection: IAreaFormacao[] = [sampleWithPartialData];
        expectedResult = service.addAreaFormacaoToCollectionIfMissing(areaFormacaoCollection, areaFormacao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaFormacao);
      });

      it('should add only unique AreaFormacao to an array', () => {
        const areaFormacaoArray: IAreaFormacao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const areaFormacaoCollection: IAreaFormacao[] = [sampleWithRequiredData];
        expectedResult = service.addAreaFormacaoToCollectionIfMissing(areaFormacaoCollection, ...areaFormacaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const areaFormacao: IAreaFormacao = sampleWithRequiredData;
        const areaFormacao2: IAreaFormacao = sampleWithPartialData;
        expectedResult = service.addAreaFormacaoToCollectionIfMissing([], areaFormacao, areaFormacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaFormacao);
        expect(expectedResult).toContain(areaFormacao2);
      });

      it('should accept null and undefined values', () => {
        const areaFormacao: IAreaFormacao = sampleWithRequiredData;
        expectedResult = service.addAreaFormacaoToCollectionIfMissing([], null, areaFormacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaFormacao);
      });

      it('should return initial array if no AreaFormacao is added', () => {
        const areaFormacaoCollection: IAreaFormacao[] = [sampleWithRequiredData];
        expectedResult = service.addAreaFormacaoToCollectionIfMissing(areaFormacaoCollection, undefined, null);
        expect(expectedResult).toEqual(areaFormacaoCollection);
      });
    });

    describe('compareAreaFormacao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAreaFormacao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAreaFormacao(entity1, entity2);
        const compareResult2 = service.compareAreaFormacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAreaFormacao(entity1, entity2);
        const compareResult2 = service.compareAreaFormacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAreaFormacao(entity1, entity2);
        const compareResult2 = service.compareAreaFormacao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
