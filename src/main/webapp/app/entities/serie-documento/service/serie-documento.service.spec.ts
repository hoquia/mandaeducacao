import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISerieDocumento } from '../serie-documento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../serie-documento.test-samples';

import { SerieDocumentoService } from './serie-documento.service';

const requireRestSample: ISerieDocumento = {
  ...sampleWithRequiredData,
};

describe('SerieDocumento Service', () => {
  let service: SerieDocumentoService;
  let httpMock: HttpTestingController;
  let expectedResult: ISerieDocumento | ISerieDocumento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SerieDocumentoService);
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

    it('should create a SerieDocumento', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const serieDocumento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(serieDocumento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SerieDocumento', () => {
      const serieDocumento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(serieDocumento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SerieDocumento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SerieDocumento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SerieDocumento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSerieDocumentoToCollectionIfMissing', () => {
      it('should add a SerieDocumento to an empty array', () => {
        const serieDocumento: ISerieDocumento = sampleWithRequiredData;
        expectedResult = service.addSerieDocumentoToCollectionIfMissing([], serieDocumento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(serieDocumento);
      });

      it('should not add a SerieDocumento to an array that contains it', () => {
        const serieDocumento: ISerieDocumento = sampleWithRequiredData;
        const serieDocumentoCollection: ISerieDocumento[] = [
          {
            ...serieDocumento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSerieDocumentoToCollectionIfMissing(serieDocumentoCollection, serieDocumento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SerieDocumento to an array that doesn't contain it", () => {
        const serieDocumento: ISerieDocumento = sampleWithRequiredData;
        const serieDocumentoCollection: ISerieDocumento[] = [sampleWithPartialData];
        expectedResult = service.addSerieDocumentoToCollectionIfMissing(serieDocumentoCollection, serieDocumento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(serieDocumento);
      });

      it('should add only unique SerieDocumento to an array', () => {
        const serieDocumentoArray: ISerieDocumento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const serieDocumentoCollection: ISerieDocumento[] = [sampleWithRequiredData];
        expectedResult = service.addSerieDocumentoToCollectionIfMissing(serieDocumentoCollection, ...serieDocumentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const serieDocumento: ISerieDocumento = sampleWithRequiredData;
        const serieDocumento2: ISerieDocumento = sampleWithPartialData;
        expectedResult = service.addSerieDocumentoToCollectionIfMissing([], serieDocumento, serieDocumento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(serieDocumento);
        expect(expectedResult).toContain(serieDocumento2);
      });

      it('should accept null and undefined values', () => {
        const serieDocumento: ISerieDocumento = sampleWithRequiredData;
        expectedResult = service.addSerieDocumentoToCollectionIfMissing([], null, serieDocumento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(serieDocumento);
      });

      it('should return initial array if no SerieDocumento is added', () => {
        const serieDocumentoCollection: ISerieDocumento[] = [sampleWithRequiredData];
        expectedResult = service.addSerieDocumentoToCollectionIfMissing(serieDocumentoCollection, undefined, null);
        expect(expectedResult).toEqual(serieDocumentoCollection);
      });
    });

    describe('compareSerieDocumento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSerieDocumento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSerieDocumento(entity1, entity2);
        const compareResult2 = service.compareSerieDocumento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSerieDocumento(entity1, entity2);
        const compareResult2 = service.compareSerieDocumento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSerieDocumento(entity1, entity2);
        const compareResult2 = service.compareSerieDocumento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
