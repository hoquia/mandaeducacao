import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHistoricoSaude } from '../historico-saude.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../historico-saude.test-samples';

import { HistoricoSaudeService, RestHistoricoSaude } from './historico-saude.service';

const requireRestSample: RestHistoricoSaude = {
  ...sampleWithRequiredData,
  inicio: sampleWithRequiredData.inicio?.toJSON(),
  fim: sampleWithRequiredData.fim?.toJSON(),
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('HistoricoSaude Service', () => {
  let service: HistoricoSaudeService;
  let httpMock: HttpTestingController;
  let expectedResult: IHistoricoSaude | IHistoricoSaude[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HistoricoSaudeService);
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

    it('should create a HistoricoSaude', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const historicoSaude = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(historicoSaude).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HistoricoSaude', () => {
      const historicoSaude = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(historicoSaude).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HistoricoSaude', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HistoricoSaude', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a HistoricoSaude', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addHistoricoSaudeToCollectionIfMissing', () => {
      it('should add a HistoricoSaude to an empty array', () => {
        const historicoSaude: IHistoricoSaude = sampleWithRequiredData;
        expectedResult = service.addHistoricoSaudeToCollectionIfMissing([], historicoSaude);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(historicoSaude);
      });

      it('should not add a HistoricoSaude to an array that contains it', () => {
        const historicoSaude: IHistoricoSaude = sampleWithRequiredData;
        const historicoSaudeCollection: IHistoricoSaude[] = [
          {
            ...historicoSaude,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHistoricoSaudeToCollectionIfMissing(historicoSaudeCollection, historicoSaude);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HistoricoSaude to an array that doesn't contain it", () => {
        const historicoSaude: IHistoricoSaude = sampleWithRequiredData;
        const historicoSaudeCollection: IHistoricoSaude[] = [sampleWithPartialData];
        expectedResult = service.addHistoricoSaudeToCollectionIfMissing(historicoSaudeCollection, historicoSaude);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(historicoSaude);
      });

      it('should add only unique HistoricoSaude to an array', () => {
        const historicoSaudeArray: IHistoricoSaude[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const historicoSaudeCollection: IHistoricoSaude[] = [sampleWithRequiredData];
        expectedResult = service.addHistoricoSaudeToCollectionIfMissing(historicoSaudeCollection, ...historicoSaudeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const historicoSaude: IHistoricoSaude = sampleWithRequiredData;
        const historicoSaude2: IHistoricoSaude = sampleWithPartialData;
        expectedResult = service.addHistoricoSaudeToCollectionIfMissing([], historicoSaude, historicoSaude2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(historicoSaude);
        expect(expectedResult).toContain(historicoSaude2);
      });

      it('should accept null and undefined values', () => {
        const historicoSaude: IHistoricoSaude = sampleWithRequiredData;
        expectedResult = service.addHistoricoSaudeToCollectionIfMissing([], null, historicoSaude, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(historicoSaude);
      });

      it('should return initial array if no HistoricoSaude is added', () => {
        const historicoSaudeCollection: IHistoricoSaude[] = [sampleWithRequiredData];
        expectedResult = service.addHistoricoSaudeToCollectionIfMissing(historicoSaudeCollection, undefined, null);
        expect(expectedResult).toEqual(historicoSaudeCollection);
      });
    });

    describe('compareHistoricoSaude', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHistoricoSaude(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareHistoricoSaude(entity1, entity2);
        const compareResult2 = service.compareHistoricoSaude(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareHistoricoSaude(entity1, entity2);
        const compareResult2 = service.compareHistoricoSaude(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareHistoricoSaude(entity1, entity2);
        const compareResult2 = service.compareHistoricoSaude(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
