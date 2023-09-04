import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISequenciaDocumento } from '../sequencia-documento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../sequencia-documento.test-samples';

import { SequenciaDocumentoService, RestSequenciaDocumento } from './sequencia-documento.service';

const requireRestSample: RestSequenciaDocumento = {
  ...sampleWithRequiredData,
  data: sampleWithRequiredData.data?.format(DATE_FORMAT),
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('SequenciaDocumento Service', () => {
  let service: SequenciaDocumentoService;
  let httpMock: HttpTestingController;
  let expectedResult: ISequenciaDocumento | ISequenciaDocumento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SequenciaDocumentoService);
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

    it('should create a SequenciaDocumento', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const sequenciaDocumento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sequenciaDocumento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SequenciaDocumento', () => {
      const sequenciaDocumento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sequenciaDocumento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SequenciaDocumento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SequenciaDocumento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SequenciaDocumento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSequenciaDocumentoToCollectionIfMissing', () => {
      it('should add a SequenciaDocumento to an empty array', () => {
        const sequenciaDocumento: ISequenciaDocumento = sampleWithRequiredData;
        expectedResult = service.addSequenciaDocumentoToCollectionIfMissing([], sequenciaDocumento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sequenciaDocumento);
      });

      it('should not add a SequenciaDocumento to an array that contains it', () => {
        const sequenciaDocumento: ISequenciaDocumento = sampleWithRequiredData;
        const sequenciaDocumentoCollection: ISequenciaDocumento[] = [
          {
            ...sequenciaDocumento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSequenciaDocumentoToCollectionIfMissing(sequenciaDocumentoCollection, sequenciaDocumento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SequenciaDocumento to an array that doesn't contain it", () => {
        const sequenciaDocumento: ISequenciaDocumento = sampleWithRequiredData;
        const sequenciaDocumentoCollection: ISequenciaDocumento[] = [sampleWithPartialData];
        expectedResult = service.addSequenciaDocumentoToCollectionIfMissing(sequenciaDocumentoCollection, sequenciaDocumento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sequenciaDocumento);
      });

      it('should add only unique SequenciaDocumento to an array', () => {
        const sequenciaDocumentoArray: ISequenciaDocumento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sequenciaDocumentoCollection: ISequenciaDocumento[] = [sampleWithRequiredData];
        expectedResult = service.addSequenciaDocumentoToCollectionIfMissing(sequenciaDocumentoCollection, ...sequenciaDocumentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sequenciaDocumento: ISequenciaDocumento = sampleWithRequiredData;
        const sequenciaDocumento2: ISequenciaDocumento = sampleWithPartialData;
        expectedResult = service.addSequenciaDocumentoToCollectionIfMissing([], sequenciaDocumento, sequenciaDocumento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sequenciaDocumento);
        expect(expectedResult).toContain(sequenciaDocumento2);
      });

      it('should accept null and undefined values', () => {
        const sequenciaDocumento: ISequenciaDocumento = sampleWithRequiredData;
        expectedResult = service.addSequenciaDocumentoToCollectionIfMissing([], null, sequenciaDocumento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sequenciaDocumento);
      });

      it('should return initial array if no SequenciaDocumento is added', () => {
        const sequenciaDocumentoCollection: ISequenciaDocumento[] = [sampleWithRequiredData];
        expectedResult = service.addSequenciaDocumentoToCollectionIfMissing(sequenciaDocumentoCollection, undefined, null);
        expect(expectedResult).toEqual(sequenciaDocumentoCollection);
      });
    });

    describe('compareSequenciaDocumento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSequenciaDocumento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSequenciaDocumento(entity1, entity2);
        const compareResult2 = service.compareSequenciaDocumento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSequenciaDocumento(entity1, entity2);
        const compareResult2 = service.compareSequenciaDocumento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSequenciaDocumento(entity1, entity2);
        const compareResult2 = service.compareSequenciaDocumento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
