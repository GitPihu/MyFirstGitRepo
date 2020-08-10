package edi

import catalog.Root
import spock.lang.Shared
import util.ECWSpec

import java.sql.Connection

class ClmAdjsSpec  extends ECWSpec{

    private ClmAdjs clmAdjs;
    @Shared
    Root root = catalog.Root.createDbConnection(null)
    def setup() {
        clmAdjs = new ClmAdjs();
    }

    static def content = {
        edi_adj_codes(id:45656,code:'MARKDOWN',name:'Financial Adjustment',AdjustPtBalance:0.0,Color:'##ffff00',Inactive:0,rcmPracticeView:0)
        edi_invoice(id:45656,SubmissionId:88888,voidflag:0,balance:111,patientid:11111,vmid:getVMId(),PtStmtMsg:'test')
        edi_inv_adjustments(id:454354, InvId: 45656,code:'MARKDOWN',date:'2019-08-02', amount:8.9 )
        edi_inveob_cas(InvEobCASId: 46543, InvEobId: 998925, GroupCode: 'PR', ReasonCode: '2332', amount: 50.00, deleteFlag: 0, CreatedBy: 998925)

    }

    def "API to get  Adjustments"() {
            given: 'oRootCon,nInvId'
        when: 'clmAdjs.GetAdjustments this method called'
            def result= clmAdjs.GetAdjustments(root,45656)
        then: 'This method clmAdjs.GetAdjustments should return result not empty'
            result.contains("MARKDOWN")
    }
    def "API to Set Line Adh"() {
        def strXml="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\" xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\">\n" +
                "    <SOAP-ENV:Body>\n" +
                "        <return>\n" +
                "            <ClaimAdjustments>\n" +
                "                <ClaimId>45656</ClaimId>\n" +
                "                <PtStmtMsg>test</PtStmtMsg>\n" +
                "                <voidflag>0</voidflag>\n" +
                "                <adjs>\n" +
                "                    <adjustment>\n" +
                "                        <id>26078529</id>\n" +
                "                        <date>2019-08-02</date>\n" +
                "                        <dateFormatted>08/02/2019</dateFormatted>\n" +
                "                        <code>MARKDOWN</code>\n" +
                "                        <desc>Financial Adjustment</desc>\n" +
                "                        <AdjColor>##ffff00</AdjColor>\n" +
                "                        <SumPostedCPT>0.0</SumPostedCPT>\n" +
                "                        <amount>8.90</amount>\n" +
                "                        <amountFormatted>\$8.90</amountFormatted>\n" +
                "                        <closingId>0</closingId>\n" +
                "                        <adjlock>0</adjlock>\n" +
                "                    </adjustment>\n" +
                "                </adjs>\n" +
                "            </ClaimAdjustments>\n" +
                "            <status>success</status>\n" +
                "        </return>\n" +
                "    </SOAP-ENV:Body>\n" +
                "</SOAP-ENV:Envelope>"
            given: 'oRootCon,requestBean'
        when: 'clmAdjs.GetAdjustments this method called'
            def result= clmAdjs.SetLineAdjustment(root,45656,strXml)
        then: 'This method clmAdjs.SetLineAdjustment should return result not empty'
            result.contains("MARKDOWN")
    }
    def "API to Current AdjAmount"() {
        given: 'oRootCon,nAdjId'
        when: 'clmAdjs.getCurrentAdjAmount this method called'
            def result= clmAdjs.getCurrentAdjAmount(root,454354)
        then: 'This method clmAdjs.getCurrentAdjAmount should return result not empty'
            result==8.9
    }
    def "API to log Deleted Row"() {
        given: 'oRootCon,nAdjId'
        when: 'clmAdjs.logDeletedRow this method called'
            clmAdjs.logDeletedRow(root,454354)
        then: 'This method clmAdjs.logDeletedRow should execute successfully and not throw exception'
            notThrown(Exception)
    }
    def "API to delete AdjCASCode"() {
        given: 'oRootCon,nInvEobCASId'
        when: 'clmAdjs.deleteAdjCASCode this method called'
            def result= clmAdjs.deleteAdjCASCode(root,46543)
        then: 'This method clmAdjs.deleteAdjCASCode should execute successfully'
            result.contains("success")
    }
    def "API to delete Claim AdjCASCode"() {
        given: 'oRootCon,nInvEobCASId'
        when: 'clmAdjs.deleteClaimAdjCASCode this method called'
            def result= clmAdjs.deleteClaimAdjCASCode(root,46543)
        then: 'This method clmAdjs.deleteClaimAdjCASCode should execute successfully'
            result.contains("success")
    }
    def "API to Delete Adjustment"() {
        given: 'oRootCon,nInvEobCASId'
        when: 'clmAdjs.DeleteAdjustment this method called'
            def result= clmAdjs.DeleteAdjustment(root,454354)
        then: 'This method clmAdjs.DeleteAdjustment should execute successfully'

    }
    def "API Save Claim Adjustment"() {
        given: 'oRootCon,nInvAdjId,nInvId,strDate,strAdjCode,dAdjAmt'
        when: 'clmAdjs.SaveClaimAdjustment this method called'
            def result= clmAdjs.SaveClaimAdjustment(root,454354,655,"2020-01-01","",88.0)
        then: 'This method clmAdjs.SaveClaimAdjustment should execute successfully'

    }

    def cleanupSpec() {
        Connection con = dataSource.getConnection()
        con.createStatement().executeUpdate("DELETE FROM edi_inv_deleted_adj WHERE RefId = 454354")
        con.close()
    }
}
